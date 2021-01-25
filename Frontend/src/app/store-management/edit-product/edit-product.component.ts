import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Data, Router} from '@angular/router';
import {Product} from '../../typescript/model/product.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProductService} from '../../products/product.service';
import {displayHttpError} from '../../typescript/utils/http-error-handeling';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Watch} from '../../typescript/model/watch.model';
import {Bracelet} from '../../typescript/model/bracelet.model';
import {ConfirmWarnDialogComponent} from '../../shared/dialogs/confirm-warn-dialog.component';
import {ForceConfirmDialogComponent} from '../../shared/dialogs/force-confirm-dialog.component';

@Component({
  selector: 'app-edit-product',
  templateUrl: './edit-product.component.html',
  styleUrls: ['./edit-product.component.scss']
})
export class EditProductComponent implements OnInit {

  overheadFromGroup: FormGroup;

  productFromGroup: FormGroup;
  watchFromGroup: FormGroup;
  braceletFromGroup: FormGroup;
  newImagePath: string;

  product: Product;
  isImageTouched: boolean;
  isNew: boolean;
  image: File;
  imageFileName: string;

  constructor(
    private route: ActivatedRoute,
    private formBuilder: FormBuilder,
    private productService: ProductService,
    private dialog: MatDialog,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.isImageTouched = false;
    if (this.route.snapshot.params.id){
      this.isNew = false;
      this.grabProduct();
    } else {
      this.isNew = true;
    }
    this.formInit();
  }

  grabProduct(){
    this.route.data.subscribe(
      (data: Data) => {
        this.product = data.productResolver;
      }
    );
  }

  onBack() {
    const backDialog = this.dialog.open(ConfirmWarnDialogComponent, {
      data: {
        title: 'Return To Store Management',
        text: 'Are You Sure You Want To Go Back To Store Management? Changes Will Not Be Saved!',
        buttonText: 'Go Back'
      }
    });

    this.navigateToStoreManagement(backDialog);
  }

  onSuccessfulSave() {
    const successDialog = this.dialog.open(ForceConfirmDialogComponent, {
      data: {
        title: this.isNew ? 'Successfully Created Product' : 'Successfully Updated Product',
        text: this.isNew ?
          'The product was created with success, you will be redirected to the store management page.' :
          'The product was updated with success, you will be redirected to the store management page.',
        buttonText: 'To Store Management'
      },
    });
    this.navigateToStoreManagement(successDialog);
  }

  navigateToStoreManagement(dialog: MatDialogRef<ConfirmWarnDialogComponent>) {
    dialog.afterClosed().subscribe(result => {
      if (result) {
        this.router.navigate(['/store']);
      }
    });
  }

  // product form
  formInit(){
    this.productFormInit();
    this.watchFromInit();
    this.braceletFromInit();
    this.overheadFormInit();
  }

  overheadFormInit() {
    this.overheadFromGroup = this.formBuilder.group({
      product:  this.productFromGroup,
      watch:    this.watchFromGroup,
      bracelet: this.braceletFromGroup
    });
  }

  productFormInit() {
    this.productFromGroup = this.formBuilder.group({
      name:         [this.isNew ? '' : this.product.name,        this.standardStringValidators()],
      brand:        [this.isNew ? '' : this.product.brand,       this.standardStringValidators()],
      price:        [this.isNew ? '' : this.product.price,       this.standardNumberValidators()],
      stock:        [this.isNew ? '' : this.product.stock,       this.standardNumberValidators()],
      description:  [this.isNew ? '' : this.product.description, this.standardTextValidators()]
    });
  }

  watchFromInit() {
    this.watchFromGroup = this.formBuilder.group({
      size:         [this.isNew ? '' : this.product.watch.size,         this.standardNumberValidators()],
      material:     [this.isNew ? '' : this.product.watch.material,     this.standardStringValidators()],
      colorPointer: [this.isNew ? '' : this.product.watch.colorPointer, this.standardStringValidators()],
      colorDial:    [this.isNew ? '' : this.product.watch.colorDial,    this.standardStringValidators()]
    });
  }

  braceletFromInit() {
    this.braceletFromGroup = this.formBuilder.group({
      length:       [this.isNew ? '' : this.product.watch.watchBracelet.length,   this.standardNumberValidators()],
      material:     [this.isNew ? '' : this.product.watch.watchBracelet.material, this.standardStringValidators()],
      style:        [this.isNew ? '' : this.product.watch.watchBracelet.style,    this.standardStringValidators()],
      color:        [this.isNew ? '' : this.product.watch.watchBracelet.color,    this.standardStringValidators()]
    });
  }

  standardStringValidators() {
    return [Validators.required, Validators.minLength, Validators.maxLength(80)];
  }

  standardTextValidators() {
    return [Validators.required, Validators.minLength, Validators.maxLength(1000)];
  }

  standardNumberValidators() {
    return [Validators.required, Validators.min(1)];
  }


  // update logic
  onImageChange(event: Event){
    this.isImageTouched = true;
    const img = (event.target as HTMLInputElement).files[0];
    this.imageFileName = img.name;
    this.image = img;
  }

  makeProduct() {
    const productFromControls = this.productFromGroup.controls;
    const watchFromControls = this.watchFromGroup.controls;
    const braceletFormControls = this.braceletFromGroup.controls;

    return new Product(
      this.isNew ? 0 : this.product.productId,
      productFromControls.name.value,
      productFromControls.brand.value,
      productFromControls.price.value,
      productFromControls.description.value,
      productFromControls.stock.value,
      this.newImagePath,
      new Watch(
        this.isNew ? 0 : this.product.watch.watchId,
        watchFromControls.material.value,
        watchFromControls.size.value,
        watchFromControls.colorPointer.value,
        watchFromControls.colorDial.value,
        new Bracelet(
          this.isNew ? 0 : this.product.watch.watchBracelet.braceletId,
          braceletFormControls.length.value,
          braceletFormControls.material.value,
          braceletFormControls.style.value,
          braceletFormControls.color.value
        )
      )
    );
  }

  onSave() {
    if (!this.isNew && !this.isImageTouched) {
      this.newImagePath = this.product.imagePath;
      this.update();
    } else {
      this.saveImage();
    }
  }

  saveImage() {
    this.productService.uploadImage(this.image).subscribe(
      response => {
        this.newImagePath = response.imageFileName;
        this.save();
      },
      error => {
        displayHttpError(error, this.dialog);
      }
    );
  }

  save() {
    if (this.isNew){
      this.create();
    } else {
      this.update();
    }
  }

  create() {
    this.productService.createProduct(this.makeProduct()).subscribe(
      () => this.onSuccessfulSave(),
      error => displayHttpError(error, this.dialog)
    );
  }

  update() {
    this.productService.updateProduct(this.makeProduct()).subscribe(
      () => this.onSuccessfulSave(),
      error => displayHttpError(error, this.dialog)
    );
  }

}

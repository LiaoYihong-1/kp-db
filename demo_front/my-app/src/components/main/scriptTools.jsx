import React, {useState} from "react";
import Button from "@material-ui/core/Button";
import {makeStyles} from "@material-ui/core/styles";
import $ from "jquery";
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import TextField from "@material-ui/core/TextField";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import Modal from "@material-ui/core/Modal";
import Backdrop from "@material-ui/core/Backdrop";
import Fade from "@material-ui/core/Fade";
const useStyles = makeStyles((theme) => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },
    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },
}));
const useStylesForm = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
    margin: {
        margin: theme.spacing(1),
    }
}));
function AddCondition() {
    const classes = useStylesForm();
    const [state,setState] = useState({
        condition:''
    })
    const handleChange = (event) => {
        const name = event.target.name;
        setState({
            ...state,
            [name]: event.target.value,
        });
    };
    function addCondition(condition){
        $.ajax({
                url: "api/addscript",
                method:"POST",
                data:{
                    condition:condition,
                    user_id: sessionStorage.getItem("id"),
                    type:'condition'
                },
                async:false,
                success:function (res){
                    if(res.success){
                        alert('success')
                    }else {
                        //dispatch(clearAccount());
                        alert(res.message);
                    }
                }
            }
        );
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Add Conditional Script
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                onChange={handleChange}
                                variant="outlined"
                                required
                                fullWidth
                                name="condition"
                                label="Condition"
                                id="condition"
                                autoComplete="current-condition"
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={()=>{addCondition(state.condition);}}
                    >
                        Add
                    </Button>
                </form>
            </div>
        </Container>
    );
}
function AddShare() {
    const classes = useStylesForm();
    const [state,setState] = useState({
        share:''
    })
    const handleChange = (event) => {
        const name = event.target.name;
        setState({
            ...state,
            [name]: event.target.value,
        });
    };
    function addShare(share){
        $.ajax({
                url: "api/share",
                method:"POST",
                data:{
                    shareid:share,
                    user_id: sessionStorage.getItem("id"),
                    type:'condition'
                },
                async:false,
                success:function (res){
                    if(res.success){
                        alert('success')
                    }else {
                        //dispatch(clearAccount());
                        alert(res.message);
                    }
                }
            }
        );
    }

    return (
        <Container component="main" maxWidth="xs">
            <CssBaseline />
            <div className={classes.paper}>
                <Typography component="h1" variant="h5">
                    Share Other's Script
                </Typography>
                <form className={classes.form} noValidate>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                onChange={handleChange}
                                variant="outlined"
                                required
                                fullWidth
                                name="share"
                                label="Share id"
                                id="share"
                                autoComplete="current-share"
                                type = 'number'
                            />
                        </Grid>
                    </Grid>
                    <Button
                        type="submit"
                        fullWidth
                        variant="contained"
                        color="primary"
                        className={classes.submit}
                        onClick={()=>{addShare(state.share);}}
                    >
                        Add
                    </Button>
                </form>
            </div>
        </Container>
    );
}

function TransitionsModal() {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [openShare, setOpenShare] = React.useState(false);
    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const handleOpenShare = () => {
        setOpenShare(true);
    };

    const handleCloseShare = () => {
        setOpenShare(false);
    };

    return (
        <div>
            <Button type="button" onClick={handleOpen} color={"inherit"}>
                Add Condition script
            </Button>
            <Button type="button" onClick={handleOpenShare} color={"inherit"}>
                Share Other's Condition Script
            </Button>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={open}
                onClose={handleClose}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade in={open}>
                    <div className={classes.paper}>
                        <AddCondition/>
                    </div>
                </Fade>
            </Modal>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={openShare}
                onClose={handleCloseShare}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade in={openShare}>
                    <div className={classes.paper}>
                        <AddShare/>
                    </div>
                </Fade>
            </Modal>
        </div>
    );
}

export default function ScriptTools(){
    return(
        <TransitionsModal/>
    )
}